import { Component, OnInit } from '@angular/core';
import { forkJoin } from 'rxjs';
import { Accommodation } from 'src/app/models/accommodation';
import { Booking } from 'src/app/models/booking';
import { Page } from 'src/app/models/page';

import { Room } from 'src/app/models/room';
import { AccommodationService } from 'src/app/services/accommodation.service';
import { BookingService } from 'src/app/services/booking.service';
import { RoomService } from 'src/app/services/room.service';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.scss'],
})
export class BookingComponent implements OnInit {
  bookings: Booking[] = [];
  rooms: Room[] = [];
  accommodations: Accommodation[] = [];
  editingBooking: any = null;
  newBooking: any = {};
  showCard: boolean = false;
  selectedOrder: string = 'ricerca';
  searchTerm: string = '';
  fullName: string = '';
  phone: string = '';
  partialName: string = '';

  page!: Page<Booking>;
  currentPage: number = 0;
  totalPages!: number;

  constructor(
    private bookingSrv: BookingService,
    private roomSrv: RoomService,
    private AccSrv: AccommodationService
  ) {}

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(page: number = 0, size: number = 3) {
    if (this.selectedOrder === 'findByEmail') {
      this.bookingSrv.findByEmail(this.searchTerm).subscribe(
        (bookings) => {
          this.bookings = bookings;
        },
        (error) => {
          console.error('Errore durante il recupero delle prenotazioni', error);
        }
      );
    } else if (this.selectedOrder === 'findByFullNameAndPhone') {
      this.bookingSrv
        .findByFullNameAndPhone(this.fullName, this.phone)
        .subscribe(
          (bookings) => {
            this.bookings = bookings;
          },
          (error) => {
            console.error(
              'Errore durante il recupero delle prenotazioni',
              error
            );
          }
        );
    } else if (this.selectedOrder === 'findByPartialName') {
      this.bookingSrv.findByPartialName(this.partialName).subscribe(
        (bookings) => {
          this.bookings = bookings;
        },
        (error) => {
          console.error('Errore durante il recupero delle prenotazioni', error);
        }
      );
    } else {
      forkJoin([
        this.bookingSrv.getBooking(page, size),
        this.roomSrv.getRoom(),
        this.AccSrv.getAccommodation(),
      ]).subscribe(
        ([bookingsPage, rooms, accommodations]: [Page<any>, any, any]) => {
          this.bookings = bookingsPage.content;
          this.page = bookingsPage;
          this.currentPage = bookingsPage.number;
          this.totalPages = bookingsPage.totalPages;

          this.rooms = rooms.content;
          this.accommodations = accommodations;

          console.log(this.currentPage);
          console.log(this.totalPages);
        },
        (error) => {
          console.error(
            'Errore durante il recupero delle prenotazioni e delle stanze',
            error
          );
        }
      );
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.fetchData(this.currentPage);
    }
  }
  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.fetchData(this.currentPage);
    }
  }

  addNewBooking() {
    this.bookingSrv.createBooking(this.newBooking).subscribe(
      (response) => {
        this.bookings.push(response);
        this.newBooking = {};
        this.showCard = false;
      },
      (error) => {
        console.error(
          "Errore durante l'aggiunta del nuovo tipo di stanza:",
          error
        );
      }
    );
  }
  toggleCardVisibility() {
    this.showCard = !this.showCard;
  }
  close() {
    this.showCard = false;
  }

  editBooking(booking: any) {
    const selectedBookingId = booking.id;
    this.editingBooking = { ...booking, id: selectedBookingId };
  }

  saveEditedBooking() {
    this.bookingSrv
      .updateBooking(this.editingBooking.id, this.editingBooking)
      .subscribe(
        (updatedBooking) => {
          console.log('Prenotazione aggiornata con successo:', updatedBooking);
          this.cancelEdit();
          this.fetchData();
        },
        (error) => {
          console.error(
            "Errore durante l'aggiornamento della prenotazione:",
            error
          );
        }
      );
  }

  cancelEdit() {
    this.editingBooking = null;
  }

  deleteBooking(bookingsToDelete: Booking) {
    const selectedBookingId = bookingsToDelete.id;
    this.bookingSrv.deleteBooking(selectedBookingId).subscribe(() => {
      const index = this.bookings.findIndex(
        (b: Booking) => b.id === selectedBookingId
      );
      if (index !== -1) {
        this.bookings.splice(index, 1);
      }
    });
  }

  calculateTotalCost(booking: Booking): number {
    const checkInDate = new Date(booking.checkIn);
    const checkOutDate = new Date(booking.checkOut);
    const daysBooked =
      (checkOutDate.getTime() - checkInDate.getTime()) / (1000 * 3600 * 24);
    return booking.room.price * daysBooked;
  }
}

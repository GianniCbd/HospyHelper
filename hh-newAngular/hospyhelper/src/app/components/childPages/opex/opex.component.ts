import { Component, OnInit } from '@angular/core';
import { Accommodation } from 'src/app/models/accommodation';
import { Opex } from 'src/app/models/opex';
import { AccommodationService } from 'src/app/services/accommodation.service';
import { OpexService } from 'src/app/services/opex.service';

@Component({
  selector: 'app-opex',
  templateUrl: './opex.component.html',
  styleUrls: ['./opex.component.scss'],
})
export class OpexComponent implements OnInit {
  opex: Opex[] = [];
  showCard: boolean = false;
  newOpex: any = {};
  accommodation: Accommodation[] = [];
  accommodationId!: number;
  editingOpex: any = null;

  constructor(
    private opexSrv: OpexService,
    private accommodationService: AccommodationService
  ) {}

  ngOnInit(): void {
    this.fetchAccommodation();
    this.fetchOpex();
  }

  fetchAccommodation() {
    this.accommodationService.getAccommodation().subscribe(
      (data: Accommodation[]) => {
        this.accommodation = data;
      },
      (error) => {
        console.error('Error retrieving rooms', error);
      }
    );
  }
  fetchOpex() {
    this.opexSrv.getOpex().subscribe(
      (data) => {
        this.opex = data;
      },
      (error) => {
        console.error('Errore durante il recupero dei tipi di stanza:', error);
      }
    );
  }

  selectAccommodation(accommodation: Accommodation): void {
    this.accommodationId = accommodation.id;
  }
  addNewOpex() {
    console.log('accommodationId:', this.accommodationId);
    this.opexSrv.saveOpex(this.newOpex, this.accommodationId).subscribe(
      (response) => {
        this.opex.push(response);
        this.newOpex = {};
        this.showCard = false;
      },
      (error) => {
        console.error("Errore durante l'aggiunta del dipendente:", error);
      }
    );
  }

  toggleCardVisibility() {
    this.showCard = !this.showCard;
  }
  close() {
    this.showCard = false;
  }
  editOp(opex: any) {
    console.log('opex object:', opex);
    const selectedOpexId = opex.id;
    console.log('Selected opex ID:', selectedOpexId);

    this.editingOpex = { ...opex, id: selectedOpexId };
  }
  saveEditedOp() {
    this.opexSrv.updateOpex(this.editingOpex.id, this.editingOpex).subscribe(
      (updatedOp) => {
        console.log('Stanza aggiornata con successo:', updatedOp);
        this.cancelEdit();
        this.fetchOpex();
      },
      (error) => {
        console.error("Errore durante l'aggiornamento della stanza:", error);
      }
    );
  }
  cancelEdit() {
    this.editingOpex = null;
  }

  deleteOpex(opexToDelete: Opex) {
    if (opexToDelete && opexToDelete.id) {
      const selectedOpexId = opexToDelete.id;
      this.opexSrv.deleteOpex(selectedOpexId).subscribe(() => {
        const index = this.opex.findIndex((o: Opex) => o.id === selectedOpexId);
        if (index !== -1) {
          this.opex.splice(index, 1);
        }
      });
    } else {
      console.error(
        "Errore: l'oggetto opexToDelete o la sua proprietà 'id' sono undefined."
      );
    }
  }
}

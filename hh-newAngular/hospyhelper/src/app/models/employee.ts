export interface Employee {
  id: number;
  name: string;
  surname: string;
  age: number;
  email: string;
  salary: number;
  roleEmployee?: RoleEmployee;
  showDetails?: boolean;
}
export enum RoleEmployee {
  RECEPTIONIST = 'RECEPTIONIST',
  CONCIERGE = 'CONCIERGE',
  CLEANER = 'CLEANER',
  HR = 'HR',
  WAITER = 'WAITER',
  WAITRESS = 'WAITRESS',
  MANAGER = 'MANAGER',
  CHEF = 'CHEF',
  KITCHEN_PORTER = 'KITCHEN_PORTER',
}

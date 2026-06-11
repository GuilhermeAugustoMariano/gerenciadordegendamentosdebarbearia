import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

declare global {
  interface Window {
    BARBEARIA_API_URL?: string;
  }
}

export interface CustomerResponse {
  id: number;
  name: string;
  phone: string;
}

export interface BarberResponse {
  id: number;
  name: string;
}

export interface AppointmentResponse {
  id: number;
  customerId: number;
  barberId: number;
  appointmentDate: string;
  appointmentTime: string;
  status: 'SCHEDULED' | 'CANCELED';
}

export interface AvailableTimesResponse {
  availableTimes: string[];
}

@Injectable({ providedIn: 'root' })
export class BarberShopApi {
  private readonly apiBaseUrl = (window.BARBEARIA_API_URL ?? '').replace(/\/$/, '');

  constructor(private readonly http: HttpClient) {}

  createCustomer(name: string, phone: string): Observable<CustomerResponse> {
    return this.http.post<CustomerResponse>(this.apiUrl('/customers'), { name, phone });
  }

  createBarber(name: string): Observable<BarberResponse> {
    return this.http.post<BarberResponse>(this.apiUrl('/barbers'), { name });
  }

  createAvailability(barberId: number, dayOfWeek: string, startTime: string, endTime: string): Observable<unknown> {
    return this.http.post(this.apiUrl(`/barbers/${barberId}/availabilities`), { dayOfWeek, startTime, endTime });
  }

  findAvailableTimes(barberId: number, date: string): Observable<AvailableTimesResponse> {
    return this.http.get<AvailableTimesResponse>(this.apiUrl(`/barbers/${barberId}/available-times?date=${date}`));
  }

  createAppointment(customerId: number, barberId: number, appointmentDate: string, appointmentTime: string): Observable<AppointmentResponse> {
    return this.http.post<AppointmentResponse>(this.apiUrl('/appointments'), {
      customerId,
      barberId,
      appointmentDate,
      appointmentTime
    });
  }

  cancelAppointment(appointmentId: number): Observable<AppointmentResponse> {
    return this.http.patch<AppointmentResponse>(this.apiUrl(`/appointments/${appointmentId}/cancel`), {});
  }

  private apiUrl(path: string): string {
    if (this.apiBaseUrl) {
      return `${this.apiBaseUrl}${path}`;
    }

    return `/api${path}`;
  }
}
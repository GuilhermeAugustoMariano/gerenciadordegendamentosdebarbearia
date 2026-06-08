import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

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
  constructor(private readonly http: HttpClient) {}

  createCustomer(name: string, phone: string): Observable<CustomerResponse> {
    return this.http.post<CustomerResponse>('/api/customers', { name, phone });
  }

  createBarber(name: string): Observable<BarberResponse> {
    return this.http.post<BarberResponse>('/api/barbers', { name });
  }

  createAvailability(barberId: number, dayOfWeek: string, startTime: string, endTime: string): Observable<unknown> {
    return this.http.post(`/api/barbers/${barberId}/availabilities`, { dayOfWeek, startTime, endTime });
  }

  findAvailableTimes(barberId: number, date: string): Observable<AvailableTimesResponse> {
    return this.http.get<AvailableTimesResponse>(`/api/barbers/${barberId}/available-times?date=${date}`);
  }

  createAppointment(customerId: number, barberId: number, appointmentDate: string, appointmentTime: string): Observable<AppointmentResponse> {
    return this.http.post<AppointmentResponse>('/api/appointments', {
      customerId,
      barberId,
      appointmentDate,
      appointmentTime
    });
  }

  cancelAppointment(appointmentId: number): Observable<AppointmentResponse> {
    return this.http.patch<AppointmentResponse>(`/api/appointments/${appointmentId}/cancel`, {});
  }
}
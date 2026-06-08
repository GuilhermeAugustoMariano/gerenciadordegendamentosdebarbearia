import { Component, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BarberResponse, BarberShopApi, CustomerResponse, AppointmentResponse } from './api';

interface WeekDayOption {
  value: string;
  label: string;
}

@Component({
  selector: 'app-root',
  imports: [
    ReactiveFormsModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatListModule,
    MatSelectModule,
    MatSnackBarModule,
    MatToolbarModule
  ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly customers = signal<CustomerResponse[]>([]);
  protected readonly barbers = signal<BarberResponse[]>([]);
  protected readonly availableTimes = signal<string[]>([]);
  protected readonly appointments = signal<AppointmentResponse[]>([]);

  protected readonly customerForm;
  protected readonly barberForm;
  protected readonly availabilityForm;
  protected readonly appointmentForm;

  protected readonly weekDays: WeekDayOption[] = [
    { value: 'MONDAY', label: 'Segunda-feira' },
    { value: 'TUESDAY', label: 'Terca-feira' },
    { value: 'WEDNESDAY', label: 'Quarta-feira' },
    { value: 'THURSDAY', label: 'Quinta-feira' },
    { value: 'FRIDAY', label: 'Sexta-feira' },
    { value: 'SATURDAY', label: 'Sabado' },
    { value: 'SUNDAY', label: 'Domingo' }
  ];

  constructor(
    private readonly formBuilder: FormBuilder,
    private readonly api: BarberShopApi,
    private readonly snackBar: MatSnackBar
  ) {
    this.customerForm = this.formBuilder.nonNullable.group({
      name: ['', Validators.required],
      phone: ['', Validators.required]
    });

    this.barberForm = this.formBuilder.nonNullable.group({
      name: ['', Validators.required]
    });

    this.availabilityForm = this.formBuilder.nonNullable.group({
      barberId: [0, Validators.min(1)],
      dayOfWeek: ['FRIDAY', Validators.required],
      startTime: ['09:00', Validators.required],
      endTime: ['18:00', Validators.required]
    });

    this.appointmentForm = this.formBuilder.nonNullable.group({
      customerId: [0, Validators.min(1)],
      barberId: [0, Validators.min(1)],
      appointmentDate: ['2026-06-05', Validators.required],
      appointmentTime: ['', Validators.required]
    });
  }

  protected createCustomer(): void {
    if (this.customerForm.invalid) {
      this.showMessage('Preencha nome e telefone do cliente.');
      return;
    }

    const value = this.customerForm.getRawValue();
    this.api.createCustomer(value.name, value.phone).subscribe({
      next: (customer) => {
        this.customers.update((customers) => [...customers, customer]);
        this.appointmentForm.patchValue({ customerId: customer.id });
        this.customerForm.reset();
        this.showMessage('Cliente cadastrado.');
      },
      error: (error) => this.showError(error)
    });
  }

  protected createBarber(): void {
    if (this.barberForm.invalid) {
      this.showMessage('Preencha o nome do barbeiro.');
      return;
    }

    const value = this.barberForm.getRawValue();
    this.api.createBarber(value.name).subscribe({
      next: (barber) => {
        this.barbers.update((barbers) => [...barbers, barber]);
        this.availabilityForm.patchValue({ barberId: barber.id });
        this.appointmentForm.patchValue({ barberId: barber.id });
        this.barberForm.reset();
        this.showMessage('Barbeiro cadastrado.');
      },
      error: (error) => this.showError(error)
    });
  }

  protected createAvailability(): void {
    if (this.availabilityForm.invalid) {
      this.showMessage('Escolha barbeiro, dia e horario de trabalho.');
      return;
    }

    const value = this.availabilityForm.getRawValue();
    this.api.createAvailability(
      value.barberId,
      value.dayOfWeek,
      this.toApiTime(value.startTime),
      this.toApiTime(value.endTime)
    ).subscribe({
      next: () => this.showMessage('Disponibilidade cadastrada.'),
      error: (error) => this.showError(error)
    });
  }

  protected findAvailableTimes(): void {
    const barberId = this.appointmentForm.controls.barberId.value;
    const appointmentDate = this.appointmentForm.controls.appointmentDate.value;

    if (!barberId || !appointmentDate) {
      this.showMessage('Escolha barbeiro e data.');
      return;
    }

    this.api.findAvailableTimes(barberId, appointmentDate).subscribe({
      next: (response) => this.availableTimes.set(response.availableTimes),
      error: (error) => this.showError(error)
    });
  }

  protected selectAvailableTime(time: string): void {
    this.appointmentForm.patchValue({ appointmentTime: time });
  }

  protected createAppointment(): void {
    if (this.appointmentForm.invalid) {
      this.showMessage('Escolha cliente, barbeiro, data e horario.');
      return;
    }

    const value = this.appointmentForm.getRawValue();
    this.api.createAppointment(
      value.customerId,
      value.barberId,
      value.appointmentDate,
      this.toApiTime(value.appointmentTime)
    ).subscribe({
      next: (appointment) => {
        this.appointments.update((appointments) => [...appointments, appointment]);
        this.showMessage('Agendamento criado.');
        this.findAvailableTimes();
      },
      error: (error) => this.showError(error)
    });
  }

  protected cancelAppointment(appointmentId: number): void {
    this.api.cancelAppointment(appointmentId).subscribe({
      next: (canceledAppointment) => {
        this.appointments.update((appointments) =>
          appointments.map((appointment) => appointment.id === canceledAppointment.id ? canceledAppointment : appointment)
        );
        this.showMessage('Agendamento cancelado.');
        this.findAvailableTimes();
      },
      error: (error) => this.showError(error)
    });
  }

  private toApiTime(time: string): string {
    return time.length === 5 ? `${time}:00` : time;
  }

  private showMessage(message: string): void {
    this.snackBar.open(message, 'OK', { duration: 3000 });
  }

  private showError(error: HttpErrorResponse): void {
    const message = error.error?.message ?? 'Nao foi possivel concluir a operacao.';
    this.showMessage(message);
  }
}

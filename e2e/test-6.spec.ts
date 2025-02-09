// import { test, expect } from '@playwright/test';

// test('test', async ({ page }) => {
//   await page.goto('https://highend-1.onrender.com/dashboard');
//   await page.getByRole('button', { name: 'Login' }).click();
//   await page.getByLabel('Email address').click();
//   await page.getByLabel('Email address').fill('j');
//   await page.getByLabel('Password').click();
//   await page.getByLabel('Password').press('CapsLock');
//   await page.getByLabel('Password').fill('Admin33##');
//   await page.getByRole('button', { name: 'Continue', exact: true }).click();
//   await page.getByRole('link', { name: 'Profile' }).first().click();
//   await expect(page.getByText('Car Wash2/3/2025PENDINGCancel')).toBeVisible();
//   await expect(page.locator('#root')).toContainText('Car Wash2/3/2025PENDINGCancel AppointmentReschedule');
//   await page.getByRole('link', { name: 'Services' }).first().click();
//   await page.locator('div').filter({ hasText: /^Car Wash\$15\.99Book Appointment$/ }).getByRole('button').click();
//   await page.locator('input[name="appointmentDate"]').fill('2025-02-04');
//   await page.locator('input[name="appointmentTime"]').click();
//   await page.locator('input[name="appointmentTime"]').fill('12:00');
//   await page.locator('input[name="appointmentEndTime"]').click();
//   await page.locator('input[name="appointmentEndTime"]').fill('13:00');
//   await page.getByPlaceholder('Customer ID').click();
//   await page.getByPlaceholder('Customer ID').fill('a');
//   await page.getByPlaceholder('Customer Name').click();
//   await page.getByPlaceholder('Customer Name').fill('1');
//   await page.getByPlaceholder('Employee ID').click();
//   await page.getByPlaceholder('Employee ID').fill('1');
//   await page.getByPlaceholder('Employee Name').click();
//   await page.getByPlaceholder('Employee Name').click();
//   await page.getByPlaceholder('Employee Name').fill('1');
//   page.once('dialog', dialog => {
//     console.log(`Dialog message: ${dialog.message()}`);
//     dialog.dismiss().catch(() => {});
//   });
//   await page.getByRole('button', { name: 'Book Now' }).click();
// });
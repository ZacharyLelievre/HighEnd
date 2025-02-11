// import { test, expect } from '@playwright/test';

// test('test', async ({ page }) => {
//   await page.goto('https://highend-1.onrender.com/dashboard');
//   await page.getByRole('button', { name: 'Login' }).click();
//   await page.getByLabel('Email address').click();
//   await page.getByLabel('Email address').fill('jj@j.com');
//   await page.getByLabel('Password').click();
//   await page.getByLabel('Password').press('CapsLock');
//   await page.getByLabel('Password').fill('Admin33##');
//   await page.getByRole('button', { name: 'Continue', exact: true }).click();
//   await page.getByRole('link', { name: 'Services' }).first().click();
//   await page.locator('div').filter({ hasText: /^Car Wash\$15\.99Book Appointment$/ }).getByRole('button').click();
//   await page.locator('input[name="appointmentDate"]').fill('2025-02-18');
//   await expect(page.locator('input[name="appointmentDate"]')).toBeVisible();
//   await page.locator('input[name="appointmentTime"]').click();
//   await page.locator('input[name="appointmentTime"]').fill('10:00');
//   await page.locator('input[name="appointmentEndTime"]').click();
//   await page.locator('input[name="appointmentEndTime"]').fill('12:00');
//   await page.getByPlaceholder('Customer ID').click();
//   await page.getByPlaceholder('Customer ID').fill('auth0|67a1199fa9e0c4faa3ba498e');
//   await page.getByPlaceholder('Customer Name').click();
//   await page.getByPlaceholder('Customer Name').fill('j');
//   await page.getByPlaceholder('Employee ID').click();
//   await page.getByPlaceholder('Employee ID').fill('j');
//   await page.getByPlaceholder('Employee Name').click();
//   await page.getByPlaceholder('Employee Name').fill('j');
//   page.once('dialog', dialog => {
//     console.log(`Dialog message: ${dialog.message()}`);
//     dialog.dismiss().catch(() => {});
//   });
//   await page.getByRole('button', { name: 'Book Now' }).click();
//   await page.getByRole('link', { name: 'Profile' }).first().click();
//   await expect(page.locator('#root')).toContainText('2/17/2025');
// });

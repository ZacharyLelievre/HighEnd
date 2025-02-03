import { test, expect } from '@playwright/test';

// test('View Assigned Appointment As Employee', async ({ page }) => {
//   await page.goto('http://localhost:3000/home');
//   await page.getByRole('button', { name: 'Login' }).click();
//   await page.getByLabel('Email address').click();
//   await page.getByLabel('Email address').fill('ros@ros.com');
//   await page.getByLabel('Password').click();
//   await page.getByLabel('Password').fill('Admin33##');
//   await page.getByRole('button', { name: 'Continue', exact: true }).click();
//   await page.getByRole('link', { name: 'Profile' }).click();
//   const appointment = page.locator('text=Car Wash6/30/2025CONFIRMED');
//   await expect(appointment).toBeVisible();
//   await appointment.click();
// });

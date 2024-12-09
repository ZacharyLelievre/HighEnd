import { test, expect } from '@playwright/test';

test('ViewAllAppointments', async ({ page }) => {
  await page.goto('http://localhost:3000/home');
  await page.getByRole('link', { name: 'Appointments' }).click();
  await expect(page.locator('#root')).toContainText('Service Name: Car Wash');
  await page.getByRole('button', { name: 'View' }).first().click();
});
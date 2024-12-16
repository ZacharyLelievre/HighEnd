import { test, expect } from '@playwright/test';

test('Testing to assign an employee to an appointment', async ({ page }) => {
  await page.goto('http://localhost:3000/home');
  await page.getByRole('link', { name: 'Dashboard' }).click();
  await expect(page.locator('#root')).toContainText('Select EmployeeJane SmithJohn DoeEmily ClarkMichael BrownSarah DavisDavid WilsonSophia TaylorJames MooreOlivia AndersonEthan Jackson');
  await page.getByRole('button', { name: 'Assign' }).first().click();
  await expect(page.locator('#root')).toContainText('Employee Name: Jane Smith');
  await page.getByRole('button', { name: 'Confirm' }).first().click();
});
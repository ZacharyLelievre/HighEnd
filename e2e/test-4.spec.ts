import { test, expect } from '@playwright/test';
//
// test('test', async ({ page }) => {
//   await page.goto('https://high-end-detailing.com/home');
//   await page.getByRole('button', { name: 'Login' }).click();
//   await page.getByLabel('Email address').click();
//   await page.getByLabel('Email address').fill('admin@admin.com');
//   await page.getByLabel('Password').click();
//   await page.getByLabel('Password').fill('Admin33##');
//   await page.getByRole('button', { name: 'Continue', exact: true }).click();
//   await page.getByRole('link', { name: 'Dashboard' }).first().click();
//   await page.getByRole('cell', { name: 'Complete Reconditioning' }).nth(1).click();
//   await page.getByLabel('Update Status:').selectOption('COMPLETED');
//   await page.getByRole('button', { name: 'Save' }).click();
//   await page.getByText('COMPLETED', { exact: true }).click();
//   await expect(page.locator('#root')).toContainText('COMPLETED');
// });
import { test, expect } from '@playwright/test';

test('create an employee', async ({ page }) => {
  await page.goto('http://localhost:3000/home');
  await page.getByRole('button', { name: 'Login' }).click();
  await page.getByLabel('Email address').click();
  await page.getByLabel('Email address').fill('admin@admin.com');
  await page.getByLabel('Password').click();
  await page.getByLabel('Password').fill('Admin33##');
  await page.getByRole('button', { name: 'Continue', exact: true }).click();
  await page.getByRole('link', { name: 'Dashboard' }).first().click();
  await page.getByRole('button', { name: 'Generate Employee Invite' }).click();
  await expect(page.locator('#root')).toContainText('Share this link with your new employee:');
});
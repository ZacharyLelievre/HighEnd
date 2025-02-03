import { test, expect } from '@playwright/test';

test('View Customer details', async ({ page }) => {
  await page.goto('http://localhost:3000/home');
  await page.getByRole('button', { name: 'Login' }).click();
  await page.getByLabel('Email address').click();
  await page.getByLabel('Email address').fill('admin@admin.com');
  await page.getByLabel('Password').click();
  await page.getByLabel('Password').fill('Admin33##');
  await page.getByRole('button', { name: 'Continue', exact: true }).click();
  await page.getByRole('link', { name: 'Dashboard' }).first().click();
  await page.locator('button:nth-child(6)').first().click();
  await expect(page.getByRole('heading')).toContainText('John Doe');
});
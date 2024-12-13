import { test, expect } from '@playwright/test';

test('Validate view customer details', async ({ page }) => {
  await page.goto('http://localhost:3000/home');
  await page.getByRole('link', { name: 'Dashboard' }).click();
  await page.getByRole('link', { name: 'Jane Smith' }).click();
  await expect(page.getByText('Jane Smithjane.smith@example.comFirst NameLast NamePostal')).toBeVisible();
});
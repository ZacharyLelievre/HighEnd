import { test, expect } from '@playwright/test';

test('Validate the existence of the services page and it displays the difference services', async ({ page }) => {
  await page.goto('http://localhost:3000/home');
  await expect(page.getByRole('link', { name: 'Services' })).toBeVisible();
  await page.getByRole('link', { name: 'Services' }).click();
  await expect(page.getByText('Car Wash$')).toBeVisible();
});
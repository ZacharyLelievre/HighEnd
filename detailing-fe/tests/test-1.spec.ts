import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('http://localhost:3000/home');
  await page.getByRole('link', { name: 'Services' }).click();
  await expect(page.locator('div').filter({ hasText: /^Car Wash\$15\.99$/ })).toBeVisible();
});
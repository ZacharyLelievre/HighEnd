import { test, expect } from '@playwright/test';

test('image appears on the Gallery page', async ({ page }) => {
  // Go to the home page
  await page.goto('http://localhost:3000/home');

  // Click the 'Gallery' link
  await page.getByRole('link', { name: 'Gallery' }).click();

  // Click on the 'Interior Detailing' image link or element
  const interiorDetailingImage = page.getByRole('img', { name: 'Interior Detailing' });
  await interiorDetailingImage.click();

  // Assert that the image is visible on the page
  await expect(interiorDetailingImage).toBeVisible();
});
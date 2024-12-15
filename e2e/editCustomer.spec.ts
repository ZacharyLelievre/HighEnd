import test, { expect } from "@playwright/test";

test('edit customer', async ({ page }) => {
  await page.goto('http://localhost:3000/home');
  await page.getByRole('link', { name: 'Dashboard' }).click();
  await page.locator('button:nth-child(5)').first().click();
  await page.locator('input[name="customerFirstName"]').click();
  await page.locator('input[name="customerFirstName"]').fill('Jan');
  await page.getByRole('button', { name: 'Save Changes' }).click();

  // Use getByRole to specifically check for the heading with the updated name
  const nameElement = await page.getByRole('heading', { name: 'Jan Smith' });
  await expect(nameElement).toBeVisible();
});


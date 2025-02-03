 import test, { expect } from "@playwright/test";

// test('edit customer', async ({ page }) => {
//   // Step 1: Go to the home page
//   await page.goto('http://localhost:3000');

//   // Step 2: Click on the 'Dashboard' link
//   await page.getByRole('link', { name: 'Dashboard' }).click();

//   // Step 3: Click on the 'Edit Customer' button (ensure the selector is unique)
//   await page.locator('button:nth-child(5)').first().click();
//   await page.waitForSelector('input[name="customerFirstName"]');

//   // Step 4: Fill the 'First Name' input field
//   await page.locator('input[name="customerFirstName"]').fill('Jan');

//   // Step 5: Click the 'Save Changes' button
//   await page.getByRole('button', { name: 'Save Changes' }).click();

//   // Step 6: Wait for the updated name to appear in the heading
//   await page.waitForSelector('h2:has-text("Jan Doe")');

//   // Step 7: Assert the updated name is visible
//   const nameElement = await page.getByRole('heading', { name: 'Jan Doe' });
//   await expect(nameElement).toBeVisible();
// });

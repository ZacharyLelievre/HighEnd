// import { test, expect } from '@playwright/test';

// test('edit customer information e2e', async ({ page }) => {
//   await page.goto('http://localhost:3000/home');
//   await page.locator('div').filter({ hasText: 'HomesServicesGalleryLoginRegister' }).nth(2).click();
//   await page.getByLabel('Email address').click();
//   await page.getByLabel('Email address').fill('bob@bob.com');
//   await page.getByLabel('Password').click();
//   await page.getByLabel('Password').fill('Admin33##');
//   await page.getByRole('button', { name: 'Continue', exact: true }).click();
//   await page.getByRole('link', { name: 'Profile' }).click();
//   await page.getByRole('button', { name: 'Edit Profile' }).click();
  
//   await page.getByLabel('Street Address:').fill('e2e test');
  
//   await page.getByRole('button', { name: 'Save Changes' }).click();
  
//   const updatedAddress = await page.locator('input[name="streetAddress"]').inputValue();
//   expect(updatedAddress).toBe('e2e test');
// });


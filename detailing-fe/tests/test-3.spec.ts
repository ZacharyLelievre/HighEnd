import { test, expect } from "@playwright/test";

test("Add Image as Admin", async ({ page }) => {
  await page.goto("http://localhost:3000/home");
  await page.getByRole("button", { name: "Login" }).click();
  await page.getByLabel("Email address").fill("admin@admin.com");
  await page.getByLabel("Password").fill("Admin33##");
  await page.getByRole("button", { name: "Continue", exact: true }).click();
  await page.getByRole("link", { name: "Gallery" }).first().click();

  // Expect the image to be visible before clicking it
  const image = await page.getByRole("img", { name: "lol" });
  await expect(image).toBeVisible();

  // Click the image
  await image.click();
});

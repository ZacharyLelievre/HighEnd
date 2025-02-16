import { test, expect } from "@playwright/test";

test("Delete Image as Admin", async ({ page }) => {
  await page.goto("http://localhost:3000/home");
  await page.getByRole("button", { name: "Login" }).click();
  await page.getByLabel("Email address").click();
  await page.getByLabel("Email address").fill("admin@admin.com");
  await page.getByLabel("Password").click();
  await page.getByLabel("Password").fill("Admin33##");
  await page.getByRole("button", { name: "Continue", exact: true }).click();
  await page.getByRole("link", { name: "Dashboard" }).first().click();
  await page
    .locator("div:nth-child(4) > .gallery-item > .delete-button")
    .click();

  // Assertion to check if the success message is visible
  await expect(page.getByText("Image deleted successfully")).toBeVisible();
});

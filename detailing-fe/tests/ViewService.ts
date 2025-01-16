import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/home");
  await page.getByRole("link", { name: "Services" }).click();
  await expect(page.locator("#root")).toContainText("Interior Cleaning");
  await page
    .locator("div")
    .filter({ hasText: /^Interior Cleaning\$25\.00$/ })
    .click();
  await expect(page.locator("h1")).toContainText("Interior Cleaning");
});

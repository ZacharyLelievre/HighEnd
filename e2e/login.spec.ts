import { test, expect } from "@playwright/test";

test("Login", async ({ page }) => {
  await page.goto("https://highend-1.onrender.com/home");
  await page.getByRole("button", { name: "Login" }).click();
  await page.getByLabel("Email address").click();
  await page.getByLabel("Email address").fill("admin@admin.com");
  await page.getByLabel("Password").click();
  await page.getByLabel("Password").fill("Admin33##");
  await page.getByRole("button", { name: "Continue", exact: true }).click();
  await expect(page.locator("#basic-navbar-nav")).toContainText("Dashboard");
});

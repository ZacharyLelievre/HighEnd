import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/home");
  await page.getByRole("link", { name: "Dashboard" }).click();
  await expect(page.locator("#root")).toContainText(
    "Email: jane.smith@example.com",
  );
  await page.locator(".employee-details > button").first().click();
  await expect(page.locator("#root")).toContainText("jane.smith@example.com");
});

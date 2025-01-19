import { test, expect } from "@playwright/test";

test("Testing viewing all employees in dashboard", async ({ page }) => {
  await page.goto("http://localhost:3000/home");
  await page.getByRole("link", { name: "Dashboard" }).click();
  await page.locator(".employee-details > button").first().click();
  await expect(page.locator("#root")).toContainText(
    "Email: jane.smith@example.com",
  );
});

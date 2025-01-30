import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/home");
  await page.getByRole("button", { name: "Login" }).click();
  await page.getByLabel("Email address").click();
  await page.getByLabel("Email address").fill("a");
  await page.getByLabel("Password").click();
  await page.getByLabel("Password").press("CapsLock");
  await page.getByRole("button", { name: "Continue", exact: true }).click();
  await page.getByRole("link", { name: "Dashboard" }).click();

  await expect(page.locator("#root")).toContainText(
    "Date: 2025-07-01Time: 10:00Service Name: Car WashCustomer Name: John DoeEmployee Name: Jane SmithStatus: PENDINGAssign Employee:Select EmployeeEthan JacksonJane SmithJohn DoeEmily ClarkMichael BrownSarah DavisDavid WilsonSophia TaylorJames MooreOlivia AndersonAssignConfirmDelete",
  );
  await page.locator(".delete-button").first().click();
});

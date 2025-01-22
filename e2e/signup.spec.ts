import { test, expect } from "@playwright/test";

test("Signup", async ({ page }) => {
  await page.goto("https://highend-1.onrender.com/home");
  await page.getByRole("button", { name: "Register" }).click();
  await page.getByPlaceholder("First Name").click();
  await page.getByPlaceholder("First Name").fill("z");
  await page.getByPlaceholder("Last Name").click();
  await page.getByPlaceholder("Last Name").fill("l");
  await page.getByPlaceholder("Email").click();
  await page.getByPlaceholder("Email").fill("leli@gmail.com");
  await page.getByPlaceholder("Street Address").click();
  await page.getByPlaceholder("Street Address").fill("f");
  await page.getByPlaceholder("City").click();
  await page.getByPlaceholder("City").fill("f");
  await page.getByPlaceholder("Postal Code").click();
  await page.getByPlaceholder("Postal Code").fill("f");
  await page.getByPlaceholder("Street Address").click();
  await page.getByPlaceholder("Street Address").fill("fak");
  await page.getByPlaceholder("City").click();
  await page.getByPlaceholder("City").fill("fak");
  await page.getByPlaceholder("Postal Code").click();
  await page.getByPlaceholder("Province").click();
  await page.getByPlaceholder("Province").fill("q");
  await page.getByPlaceholder("Country").click();
  await page.getByPlaceholder("Country").fill("q");
  await page.getByRole("button", { name: "Submit" }).click();
  await page.getByRole("link", { name: "Sign up" }).click();
  await expect(page.locator("section")).toContainText("Log in");
  await expect(page.locator("header")).toContainText(
    "Sign Up to dev-vmtwqb6p6lr3if0d to continue to High End Detailling.",
  );
});

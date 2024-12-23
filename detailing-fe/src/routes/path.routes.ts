export enum AppRoutePath {
    Default = "/",
    Home = "/home",
    AllServicesPage = "/services",
    AllGalleriesPage = "/galleries",
    DashboardPage = "/dashboard",
    AllEmployeesPage = "/employees",
    Callback = "/callback",
    Onboarding = "/onboarding", // 🆕 Added Onboarding path

    Unauthorized = '/unauthorized',
    Forbidden = '/forbidden',
    RequestTimeout = '/request-timeout',
    InternalServerError = '/internal-server-error',
    ServiceUnavailable = '/service-unavailable'
}
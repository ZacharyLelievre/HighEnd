// File: detailing-fe/src/models/dtos/ServiceReportModel.tsx
export interface ServiceReportModel {
  serviceId: string;
  serviceName: string;
  visitors: number;
  totalBookings: number;
  totalRevenue: number;
  healthIndicator: string;
}

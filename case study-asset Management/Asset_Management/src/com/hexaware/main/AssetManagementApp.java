package com.hexaware.main;

import com.hexaware.dao.AssetManagementService;
import com.hexaware.service.AssetManagementServiceImpl;
import com.hexaware.entity.Asset;
import com.hexaware.entity.Employee;
import com.hexaware.entity.Reservation;
import com.hexaware.exception.AssetNotFoundException;
import com.hexaware.exception.AssetNotMaintainException;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AssetManagementApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AssetManagementService service = new AssetManagementServiceImpl();

        while (true) {
            System.out.println("\n=== Digital Asset Management System ===");
            System.out.println("1. Add Asset");
            System.out.println("2. Update Asset");
            System.out.println("3. Delete Asset");
            System.out.println("4. Allocate Asset");
            System.out.println("5. Deallocate Asset");
            System.out.println("6. Perform Maintenance");
            System.out.println("7. Reserve Asset");
            System.out.println("8. Withdraw Reservation");
            System.out.println("9. List All Reservations");
            System.out.println("10 Add Employee");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Asset Name: ");
                        String name = sc.nextLine();
                        System.out.print("Asset Type: ");
                        String type = sc.nextLine();
                        System.out.print("Serial Number: ");
                        String serial = sc.nextLine();
                        System.out.print("Purchase Date (YYYY-MM-DD): ");
                        LocalDate purchaseDate = LocalDate.parse(sc.nextLine());
                        System.out.print("Location: ");
                        String location = sc.nextLine();
                        System.out.print("Status: ");
                        String status = sc.nextLine();
                        System.out.print("Owner ID: ");
                        int ownerId = sc.nextInt();

                        Asset asset = new Asset(0, name, type, serial, purchaseDate, location, status, ownerId);
                        boolean added = service.addAsset(asset);
                        System.out.println(added ? "Asset added successfully." : "Failed to add asset.");
                        break;

                    case 2:
                        System.out.print("Asset ID to update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("New Name: ");
                        String newName = sc.nextLine();
                        System.out.print("New Type: ");
                        String newType = sc.nextLine();
                        System.out.print("New Serial Number: ");
                        String newSerial = sc.nextLine();
                        System.out.print("New Purchase Date (YYYY-MM-DD): ");
                        LocalDate newPurchaseDate = LocalDate.parse(sc.nextLine());
                        System.out.print("New Location: ");
                        String newLocation = sc.nextLine();
                        System.out.print("New Status: ");
                        String newStatus = sc.nextLine();
                        System.out.print("New Owner ID: ");
                        int newOwnerId = sc.nextInt();

                        Asset updatedAsset = new Asset(updateId, newName, newType, newSerial, newPurchaseDate, newLocation, newStatus, newOwnerId);
                        boolean updated = service.updateAsset(updatedAsset);
                        System.out.println(updated ? "Asset updated successfully." : "Failed to update asset.");
                        break;

                    case 3:
                        System.out.print("Asset ID to delete: ");
                        int deleteId = sc.nextInt();
                        boolean deleted = service.deleteAsset(deleteId);
                        System.out.println(deleted ? "Asset deleted successfully." : "Asset not found.");
                        break;

                    case 4:
                        System.out.print("Asset ID: ");
                        int allocAssetId = sc.nextInt();
                        System.out.print("Employee ID: ");
                        int allocEmpId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Allocation Date (YYYY-MM-DD): ");
                        LocalDate allocDate = LocalDate.parse(sc.nextLine());
                        System.out.print("Return Date (YYYY-MM-DD): ");
                        LocalDate allreturnDate = LocalDate.parse(sc.nextLine());

                        boolean allocated = service.allocateAsset(allocAssetId, allocEmpId, allocDate, allreturnDate);
                        System.out.println(allocated ? "Asset allocated successfully." : "Allocation failed.");
                        break;

                    case 5:
                        System.out.print("Asset ID: ");
                        int deallocAssetId = sc.nextInt();
                        System.out.print("Employee ID: ");
                        int deallocEmpId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Return Date (YYYY-MM-DD): ");
                        LocalDate returnDate = LocalDate.parse(sc.nextLine());
                        boolean deallocated = service.deallocateAsset(deallocAssetId, deallocEmpId, returnDate);
                        System.out.println(deallocated ? "Asset deallocated successfully." : "Deallocation failed.");
                        break;

                    case 6:
                        System.out.print("Asset ID: ");
                        int maintAssetId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Maintenance Date (YYYY-MM-DD): ");
                        LocalDate maintDate = LocalDate.parse(sc.nextLine());
                        System.out.print("Description: ");
                        String description = sc.nextLine();
                        System.out.print("Cost: ");
                        double cost = sc.nextDouble();
                        boolean maintained = service.performMaintenance(maintAssetId, maintDate, description, cost);
                        System.out.println(maintained ? "Maintenance recorded." : "Maintenance failed.");
                        break;

                    case 7:
                        System.out.print("Asset ID: ");
                        int resAssetId = sc.nextInt();
                        System.out.print("Employee ID: ");
                        int resEmpId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Reservation Date (YYYY-MM-DD): ");
                        LocalDate resDate = LocalDate.parse(sc.nextLine());
                        System.out.print("Start Date (YYYY-MM-DD): ");
                        LocalDate startDate = LocalDate.parse(sc.nextLine());
                        System.out.print("End Date (YYYY-MM-DD): ");
                        LocalDate endDate = LocalDate.parse(sc.nextLine());
                        boolean reserved = service.reserveAsset(resAssetId, resEmpId, resDate, startDate, endDate);
                        System.out.println(reserved ? "Reservation successful." : "Reservation failed.");
                        break;

                    case 8:
                        System.out.print("Reservation ID to withdraw: ");
                        int resId = sc.nextInt();
                        boolean withdrawn = service.withdrawReservation(resId);
                        System.out.println(withdrawn ? "Reservation withdrawn." : "Withdrawal failed.");
                        break;

                    case 9:
                        try {
                            List<Reservation> reservations = service.getAllReservations();
                            if (reservations.isEmpty()) {
                                System.out.println("No reservations found.");
                            } else {
                                System.out.println("\n--- All Reservations ---");
                                for (Reservation res : reservations) {
                                    System.out.println(res); // calls toString()
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Failed to retrieve reservations: " + e.getMessage());
                        }
                        break;
                        
                    case 10:  // Add Employee
                        // Get Employee information
                        System.out.print("Enter Employee Name: ");
                        String empName = sc.nextLine();

                        System.out.print("Enter Employee Department: ");
                        String empDepartment = sc.nextLine();

                        System.out.print("Enter Employee Email: ");
                        String empEmail = sc.nextLine();

                        
                        // Validate email format using the method isValidEmail
                        if (!isValidEmail(empEmail)) {
                            System.out.println("Invalid email format. Please include '@' and '.' in the email.");
                        } else {
                            // Proceed with the rest of the code if email is valid
                            System.out.print("Enter Employee Password: ");
                            String empPassword = sc.nextLine();

                            // Call the service method to add the employee
                            boolean employeeAdded = service.addEmployee(empName, empEmail, empDepartment, empPassword);

                            // Output result
                            System.out.println(employeeAdded ? "Employee added successfully." : "Failed to add employee.");
                        }
                        break;                        
                        


                    case 11:
                        System.out.println("Exiting application. Goodbye!");
                        sc.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (AssetNotFoundException | AssetNotMaintainException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static boolean isValidEmail(String empEmail) {
        // Trim the email to remove leading/trailing spaces
        empEmail = empEmail.trim();

        // Ensure that it contains "@" and "." and that "@" appears before "."
        return empEmail != null 
            && empEmail.contains("@") 
            && empEmail.contains(".") 
            && empEmail.indexOf("@") < empEmail.indexOf(".");
    }
	

	
	
	

	
}

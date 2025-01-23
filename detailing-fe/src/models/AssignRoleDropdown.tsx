import React, { useState } from 'react';
import axios from 'axios';

// Define the shape of the props
interface AssignRoleDropdownProps {
    userId: string; // Ensure this matches the type you're using for user IDs
}

const AssignRoleDropdown: React.FC<AssignRoleDropdownProps> = ({ userId }) => {
    const [selectedRoleId, setSelectedRoleId] = useState<string>("");

    const handleAssign = async () => {
        if (!selectedRoleId) {
            alert("Please select a role to assign.");
            return;
        }

        try {
            await axios.post("https://https://highenddetailing/api/auth0/assign-role", {
                auth0UserId: userId,
                roleId: selectedRoleId
            });
            alert("Role assigned successfully!");
        } catch (err) {
            console.error(err);
            alert("Failed to assign role.");
        }
    }

    return (
        <div>
            <select value={selectedRoleId} onChange={(e) => setSelectedRoleId(e.target.value)}>
                <option value="">--Select Role--</option>
                <option value="rol_abc123EMPLOYEES">EMPLOYEES</option>
                <option value="rol_abc123CUSTOMERS">CUSTOMERS</option>
                <option value="rol_abc123ADMIN">ADMIN</option>
            </select>
            <button onClick={handleAssign}>Assign Role</button>
        </div>
    )
}

export default AssignRoleDropdown;
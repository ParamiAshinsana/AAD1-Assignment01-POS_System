// // Order Save


// ----------------Get Item Code ----------------------------------------

// Assuming you have a variable to store all items
var allItems = [];

// Function to load all itemCodes into the dropdown during the initial page load
function loadItemCodes() {
    $.ajax({
        url: "http://localhost:8080/Demo_war_exploded/item",
        type: "GET",
        dataType: "json",
        success: function(data) {
            allItems = data;

            // Populate the itemCodes dropdown
            $("#itemCode").empty(); // Clear existing options
            data.forEach(function(item) {
                $("#itemCode").append("<option value='" + item.icode + "'>" + item.icode + "</option>");
            });
        },
        error: function(xhr, status, error) {
            console.error(xhr.responseText);
        }
    });
}

// Call this function to load item codes when the page loads
$(document).ready(function () {
    loadItemCodes();
});

// Add an event listener to the itemCode select box
$("#itemCode").on("change", function() {
    var selectedCode = $(this).val();

    // Find the selected item in the array
    var selectedItem = allItems.find(item => item.icode === selectedCode);

    // Update itemName and itemPrice input fields
    if (selectedItem) {
        $("#oItemName").val(selectedItem.iname);
        $("#unit_price").val(selectedItem.iprice);
    } else {
        // Handle the case where the selected item is not found
        console.error("Item not found for code: " + selectedCode);
    }
});

// ----------------Get Customer Id ----------------------------------------

// Assuming you have a variable to store all items
var allCustomers = [];

// Function to load all itemCodes into the dropdown during the initial page load
function loadCustomersIDS() {
    $.ajax({
        url: "http://localhost:8080/Demo_war_exploded/customer",
        type: "GET",
        dataType: "json",
        success: function(data) {
            allCustomers = data;

            // Populate the itemCodes dropdown
            $("#cus_Code").empty(); // Clear existing options
            data.forEach(function(customer1) {
                $("#cus_Code").append("<option value='" + customer1.id + "'>" + customer1.id + "</option>");
            });
        },
        error: function(xhr, status, error) {
            console.error(xhr.responseText);
        }
    });
}

// Call this function to load item codes when the page loads
$(document).ready(function () {
    loadCustomersIDS();
});

// Add an event listener to the itemCode select box
$("#cus_Code").on("change", function() {
    var selectedCode1 = $(this).val();

    // Find the selected item in the array
    var selectedItem1 = allCustomers.find(customer2 => customer2.id === selectedCode1);

    // Update itemName and itemPrice input fields
    if (selectedItem1) {
        $("#customerName").val(selectedItem1.name);
    } else {
        // Handle the case where the selected item is not found
        console.error("Customer not found for code: " + selectedCode1);
    }
});








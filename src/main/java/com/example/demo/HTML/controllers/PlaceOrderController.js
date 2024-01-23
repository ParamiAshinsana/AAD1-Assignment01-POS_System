// // Order Save

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





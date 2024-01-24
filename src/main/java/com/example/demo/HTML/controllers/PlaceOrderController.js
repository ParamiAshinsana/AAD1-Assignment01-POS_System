loadOrders();
var row_index = null;

// get all customers data
function loadOrders() {
    console.log("Get-paceorder");
    $("#order-tbl-body").empty();

    $.ajax({
        url: "http://localhost:8080/Demo_war_exploded/placeOrder",
        type: "GET",
        success: function(data) {
            data.forEach(function(orders) {
                let record = `<tr><td class="orderId">${orders.orderId}</td><td class="orderDate">${orders.orderDate}</td><td class="customerId">${orders.customerId}</td><td class="itemCode">${orders.itemCode}</td><td class="itemUnitPrice">${orders.itemUnitPrice}</td><td class="itemQty">${orders.itemQty}</td><td class="total">${orders.total}</td></tr>`;
                $("#order-tbl-body").append(record);
            });
        }
    });
}

// Order Save

function GetTodayDate() {
    var tdate = new Date();
    var dd = tdate.getDate(); // yields day
    var MM = tdate.getMonth() + 1; // yields month (add 1 as it is zero-based)
    var yyyy = tdate.getFullYear(); // yields year
    var currentDate = dd + "-" + MM + "-" + yyyy;

    // Set the formatted date to the input field
    $("#orderDate").val(currentDate);
}

// Call the GetTodayDate function when the document is ready
$(document).ready(function() {
    GetTodayDate();
});
$('#place_order').click(function () {
    GetTodayDate();
});
$(document).ready(function() {
    $("#order-btns> button[type='button']").eq(0).on("click", () => {
        event.preventDefault();

        let orderIdF = $("#order_id").val();
        let orderDateF = $("#orderDate").val();
        let customerIdF = $("#cus_Code").val();
        let itemCodeF = $("#itemCode").val();
        let itemPriceF = $("#unit_price").val();
        let itemQtyF = $("#qty").val();

        let totalF =  itemPriceF*itemQtyF;

        console.log('Order Id :',orderIdF);
        console.log('Order Date :',orderDateF);
        console.log('Customer Id :',customerIdF);

        console.log('Item Code :',itemCodeF);
        console.log('Item price :',itemPriceF);
        console.log('Item qty :',itemQtyF);

        console.log('Item total :',totalF);

        const orderData = {
            orderId:orderIdF,
            orderDate:orderDateF,
            customerId:customerIdF,
            itemCode:itemCodeF,
            itemUnitPrice:itemPriceF,
            itemQty:itemQtyF,
            total:totalF
        };
        console.log(orderData);

        // create JSON
        const orderJSON = JSON.stringify(orderData)
        console.log(orderJSON)

        $.ajax({
            url:"http://localhost:8080/Demo_war_exploded/placeOrder",
            type:"POST",
            data:orderJSON,
            headers:{"Content-Type":"application/json"},
            success: (res) =>{
                // clear();
                $("#order-btns>button[type='reset']").click();
                // loadCustomers();
                console.log(JSON.stringify(res))

                Swal.fire({
                    icon: 'success',
                    title: 'Order has been saved successfully!',
                    showConfirmButton: false,
                    timer: 2000
                })
            },
            error: (err)=>{
                console.error(err)
                Swal.fire({
                    icon: 'error',
                    title: 'Invalid Input',
                    text: 'Something went wrong!'
                })
            }
        });


    });
});



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


// when click a row
$("#order-tbl-body").on("click", "tr", function() {
    row_index = $(this).index();

    let orderId = $(this).find(".orderId").text();
    let orderDate = $(this).find(".orderDate").text();
    let customerId = $(this).find(".customerId").text();
    let itemCode = $(this).find(".itemCode").text();

    let itemUnitPrice = $(this).find(".itemUnitPrice").text();
    let itemQty = $(this).find(".itemQty").text();

    $("#order_id").val(orderId);
    $("#orderDate").val(orderDate);
    $("#cus_Code").val(customerId);
    $("#itemCode").val(itemCode);
    $("#unit_price").val(itemUnitPrice);
    $("#qty").val(itemQty);
})
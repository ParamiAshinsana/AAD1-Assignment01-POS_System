// // Order Save
// // $(document).ready(function() {
// //     $("#order-btns> button[type='button']").eq(0).on("click", () => {
// //         event.preventDefault();
// //
// //         let orderIdF = $("#order_id").val();
// //         let orderDateF = $("#orderDate").val();
// //         let customerIdF = $("#customer_mobile").val();
// //         let itemCodeF = $("#customer_address").val();
// //         let totalF = $("#customer_address").val();
// //
// //         console.log('Customer Id :',idF);
// //         console.log('Customer Name :',cnameF);
// //         console.log('Customer Mobile :',cMobileF);
// //         console.log('Customer Address :',cAddressF);
// //
// //         const customerData = {
// //             id:idF,
// //             name:cnameF,
// //             mobile:cMobileF,
// //             address:cAddressF
// //         };
// //         console.log(customerData);
// //
// //         // create JSON
// //         const customerJSON = JSON.stringify(customerData)
// //         console.log(customerJSON)
// //
// //         $.ajax({
// //             url:"http://localhost:8080/Demo_war_exploded/customer",
// //             type:"POST",
// //             data:customerJSON,
// //             headers:{"Content-Type":"application/json"},
// //             success: (res) =>{
// //                 // clear();
// //                 $("#order-btns>button[type='reset']").click();
// //                 // loadCustomers();
// //                 console.log(JSON.stringify(res))
// //
// //                 Swal.fire({
// //                     icon: 'success',
// //                     title: 'Order has been saved successfully!',
// //                     showConfirmButton: false,
// //                     timer: 2000
// //                 })
// //             },
// //             error: (err)=>{
// //                 console.error(err)
// //                 Swal.fire({
// //                     icon: 'error',
// //                     title: 'Invalid Input',
// //                     text: 'Something went wrong!'
// //                 })
// //             }
// //         });
// //
// //
// //     });
// // });
//
// // Load item codes function
// function loadItemCodes() {
//     $.ajax({
//         url: "http://localhost:8080/Demo_war_exploded/item",
//         type: "GET",
//         dataType: "json", // Specify the expected data type
//         success: function (data) {
//             $("#itemCode").empty(); // Clear existing options
//
//             // Check if data is an array
//             if (Array.isArray(data)) {
//                 // Add each item code to the dropdown
//                 data.forEach(function (item) {
//                     $("#itemCode").append("<option value='" + item.icode + "'>" + item.icode + "</option>");
//                 });
//             } else {
//                 console.error("Invalid data format received");
//             }
//         },
//         error: function (xhr, status, error) {
//             console.error("Error fetching item codes:", status, error);
//         }
//     });
// }
//
// // Call this function to load item codes when the page loads
// $(document).ready(function () {
//     loadItemCodes();
// });
//
// ///////////////////////////////////
//
// // Assuming you have a variable to store all items
// var allItems = [];
//
// // Populate the itemCodes dropdown during the initial page load
// loadItemCodes();
//
// // Add an event listener to the itemCode select box
// $("#itemCode").on("change", function() {
//     var selectedCode = $(this).val();
//
//     // Find the selected item in the array
//     var selectedItem = allItems.find(item => item.icode === selectedCode);
//
//     // Update itemName and itemPrice input fields
//     if (selectedItem) {
//         $("#oItemName").val(selectedItem.iname);
//         $("#unit_price").val(selectedItem.iprice);
//     } else {
//         // Handle the case where the selected item is not found
//         console.error("Item not found for code: " + selectedCode);
//     }
// });
//
// // Function to load all itemCodes into the dropdown during the initial page load
// function loadItemCodes() {
//     $.ajax({
//         url: "http://localhost:8080/Demo_war_exploded/item",
//         type: "GET",
//         dataType: "json",
//         success: function(data) {
//             allItems = data;
//
//             // Populate the itemCodes dropdown
//             data.forEach(function(item) {
//                 $("#itemCode").append("<option value='" + item.icode + "'>" + item.icode + "</option>");
//             });
//         },
//         error: function(xhr, status, error) {
//             console.error(xhr.responseText);
//         }
//     });
// }
//
//
//
//
//




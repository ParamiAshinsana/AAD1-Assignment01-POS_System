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





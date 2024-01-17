// Item Save
$(document).ready(function() {
    $("#item-btns> button[type='button']").eq(0).on("click", () => {
        event.preventDefault();

        let icodeF = $("#item_code").val();
        let inameF = $("#item_name").val();
        let ipriceF = $("#item_price").val();
        let iqtyF = $("#item_qty").val();

        console.log('Item Code :',icodeF);
        console.log('Item Name :',inameF);
        console.log('Item Price :',ipriceF);
        console.log('Item Quantity :',iqtyF);

        const itemData = {
            icode:icodeF,
            iname:inameF,
            iprice:ipriceF,
            iqty:iqtyF
        };
        console.log(itemData);

        // create JSON
        const itemJSON = JSON.stringify(itemData)
        console.log(itemJSON)

        $.ajax({
            url:"http://localhost:8080/Demo_war_exploded/item",
            type:"POST",
            data:itemJSON,
            headers:{"Content-Type":"application/json"},
            success: (res) =>{
                // clear();
                $("#item-btns>button[type='reset']").click();
                // loadCustomers();
                console.log(JSON.stringify(res))

                Swal.fire({
                    icon: 'success',
                    title: 'Item has been saved successfully!',
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
    $(document).ready(function() {
    $("#custSaveBtn > button[type='button']").eq(0).on("click", () => {
        event.preventDefault();

        let idF = $("#id").val();
        let cnameF = $("#customer_name").val();
        let cMobileF = $("#customer_mobile").val();
        let cAddressF = $("#customer_address").val();

        console.log('Customer Id :', idF);
        console.log('Customer Name :', cnameF);
        console.log('Customer Mobile :', cMobileF);
        console.log('Customer Address :', cAddressF);

        const customerData = {
            id : idF,
            name : cnameF,
            mobile : cMobileF,
            address : cAddressF
        };
        console.log(customerData);

        // create JSON
        const customerJSON = JSON.stringify(customerData)
        console.log(customerJSON)

        $.ajax({
            url:"http://localhost:8080/Demo_war_exploded/customer",
            type:"POST",
            data:customerJSON,
            headers:{"Content-Type":"application/json"},
            success: (res) =>{
                console.log(JSON.stringify(res))
            },
            error: (err)=>{
                console.error(err)
            }
        });


    });
});

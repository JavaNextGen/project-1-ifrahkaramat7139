const url = "http://localhost:7001/"
async function loginEmployee() {
    let uname=document.getElementById("username").value;
    let pwd=document.getElementById("password").value;
    let logdata={
        userName:uname,
        password:pwd

    }
    console.log(JSON.stringify(logdata));
    let response = await fetch(url + "login",{method:"POST",body:JSON.stringify(logdata),
    credentials:"include" });

    if(response.status === 202){       // var session = '<%=Session["username"] != null%>';
            //document.getElementById("loginRow").innerText=" welcome ";
        let signedinput=    document.getElementById("signed");
           signedinput.value="signed";
           signed=true;
            console.log(signed);
            document.getElementById("getEmployeeButton").addEventListener("click",getEmployees);
        }
        else {
            document.getElementById("loginRow").innerText=" refresh page and  try again ";
    }
            }
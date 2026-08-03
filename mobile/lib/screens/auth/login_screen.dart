import 'package:flutter/material.dart';

class LoginScreen extends StatefulWidget{
  const LoginScreen({super.key});
  

@override
State<LoginScreen> createState() => _LoginScreenState();
  
}

class _LoginScreenState extends State<LoginScreen>{

  final _formKey = GlobalKey<FormState>();

  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  bool obsecurePassword = true;



  @override
  Widget build(BuildContext context) {
   
   return Scaffold(
    appBar: AppBar(
      title: const Text("Login"),
    ),
    body: SafeArea(
      child: Center(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(24),
          child: Form(
            key: _formKey,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [
                const Icon(
                  Icons.agriculture,
                  size: 80,
                ),
                SizedBox(height: 20,),
                const Text(
                  "Krushi Seva Kendra",
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 28,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                SizedBox(height: 20,),

                TextFormField(
                  controller : emailController,
                  keyboardType : TextInputType.emailAddress,
                  decoration : const InputDecoration(
                    labelText: "Email",
                    hintText: "Enter your Email",
                    prefixIcon: Icon(Icons.email_outlined),
                    border: OutlineInputBorder(),
                  ),

                  validator: (value){
                    if(value == null || value.trim().isEmpty){
                      return "Please Enter your email";
                    }
                    if(!value.contains("@")){
                      return "Enter a valid email";
                    }

                    return null;
                  },
                ),
                const SizedBox(height: 20,),

                TextFormField(
                  controller: passwordController,
                  obscureText: obsecurePassword,
                  decoration: InputDecoration(
                    labelText: "Password",
                    hintText: "Enter your password",
                    prefixIcon: Icon(Icons.lock_outline),
                    border: const OutlineInputBorder(),

                    suffixIcon: IconButton( 
                    icon: Icon(
                      obsecurePassword ? Icons.visibility : Icons.visibility_off
                    ),
                    onPressed: (){
                      setState(() {
                        obsecurePassword = !obsecurePassword;
                      });
                    },
                    ),
                  ),
                  validator: (value) {
                    if(value == null || value.isEmpty){
                      return "Please Enter your password.";
                    }
                    if(value.length < 6){
                      return "Password must be at least 6 characters";
                    }

                    return null;

                  },
          
                ),

                SizedBox(height: 20,),

                SizedBox(
                
                  width: double.infinity,
                  child: ElevatedButton(onPressed:(){
                    if(_formKey.currentState!.validate()){
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(content: Text("Validation Sucessful."),
                        ),
                      );
                    }
                  }, 
                  child: const Text("LOGIN"),
                  ),
                )

                
              ],
            )),
        ),
      )
       )
   );
  }

}
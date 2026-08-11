import 'package:flutter/material.dart';
import 'package:mobile/models/login_request.dart';
import 'package:mobile/routes/app_routes.dart';
import 'package:mobile/services/api_service.dart';
import 'package:shared_preferences/shared_preferences.dart';



class LoginScreen extends StatefulWidget{
  const LoginScreen({super.key});
  

@override
State<LoginScreen> createState() => _LoginScreenState();
  
}

class _LoginScreenState extends State<LoginScreen>{

  final ApiService apiService = ApiService();

  final _formKey = GlobalKey<FormState>();

  final TextEditingController userNameController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  bool obsecurePassword = true;

  bool isLoading = false;



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
                  controller : userNameController,
                  keyboardType : TextInputType.text,
                  decoration : const InputDecoration(
                    labelText: "UserName",
                    hintText: "Enter your UserName",
                    prefixIcon: Icon(Icons.person),
                    border: OutlineInputBorder(),
                  ),

                  validator: (value){
                    if(value == null || value.trim().isEmpty){
                      return "Please Enter your username";
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
                  height: 50,
                  child: ElevatedButton(
                    onPressed: isLoading 
                    ? null 
                    :() async {
                      if(_formKey.currentState!.validate()){
                        setState(() {
                          isLoading = true;
                        });
                        try{
                          final response = await apiService.login(
                            LoginRequest(
                              username: userNameController.text.trim(), 
                              password: passwordController.text
                              ),
                          );

                          final prefs = await SharedPreferences.getInstance();

                          await prefs.setString("accessToken", response.accessToken);

                          await prefs.setString("username", response.username);

                          await prefs.setString("role",response.role);

                          await prefs.setString("tokenType", response.tokenType);

                          if(!mounted) return;

                          Navigator.pushReplacementNamed(context, AppRoutes.dashboard);

                        }
                        catch(ex){

                          ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(ex.toString())));
                          print(ex.toString());
                        }
                        finally{
                          if(mounted){
                            setState(() {
                              isLoading = false;
                            });
                          }
                        }
                      }
                    },
                    child: isLoading
                    ? const SizedBox(
                      height: 22,
                      width: 22,
                      child: CircularProgressIndicator(
                        strokeWidth: 2.5,
                      ),
                    )
                    : const Text("LOGIN"),
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
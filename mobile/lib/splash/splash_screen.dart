import "package:flutter/material.dart";
import "package:mobile/core/theme/app_colors.dart";

class SplashScreen extends StatelessWidget{

  const SplashScreen({super.key});
  
  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      backgroundColor: AppColors.primary,
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
            Icons.agriculture,
            size: 90,
            color: AppColors.white,
          ),
          SizedBox(height: 20),
          Text("Krushi Seva Kendra",
          style: TextStyle(
            color: Colors.white,
            fontSize: 28,
            fontWeight: FontWeight.bold)
          ),
          SizedBox(height: 30,),
          CircularProgressIndicator(color: Colors.white,)
          ]

        )
      ),
    );
  }
  
}
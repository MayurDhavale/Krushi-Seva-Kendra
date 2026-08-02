import 'package:flutter/material.dart';
import 'package:mobile/core/theme/app_colors.dart';


class AppThems {

  AppThems._();

  static ThemeData lightTheme = ThemeData(
    useMaterial3: true,

    primaryColor: AppColors.primary,

    scaffoldBackgroundColor: AppColors.background,

    colorScheme: ColorScheme.fromSeed(seedColor: AppColors.primary
    ),

    appBarTheme: const AppBarTheme(
      centerTitle: true,
      elevation: 0,
    )
  );
}
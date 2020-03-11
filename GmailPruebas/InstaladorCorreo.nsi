# Idiomas

# Nombre del instalador
Name "Cliente de Correo"

# The file to write
OutFile "Cliente de Correo .exe"

# The default installation directory
InstallDir $PROGRAMFILES\ClienteCorreo

# Pedimos permisos para Windows 7
RequestExecutionLevel admin

# Pantallas que hay que mostrar del instalador

Page directory
Page instfiles

#Cambiar el idioma
!include "MUI.nsh"
!insertmacro MUI_LANGUAGE "Spanish"



#Seccion principal
Section

  # Establecemos el directorio de salida al directorio de instalacion
  SetOutPath $INSTDIR\Aplicacion
  
  # Creamos el desinstalador
  writeUninstaller "$INSTDIR\uninstall.exe"
  
  # Ponemos ahi el archivo test.txt
  File /r "out\artifacts\GmailPruebas_jar\*"
  File /r "outAyudaApp"
  File /r "jasper"
  
  
  SetOutPath $INSTDIR\Aplicacion
  
  File /r "C:\Users\User\Desktop\Javax\javafx"
  
  SetOutPath $INSTDIR\Aplicacion
  
  File /r "C:\Users\User\Desktop\Javax\java-runtime"
  
  CreateShortCut "$DESKTOP\Desinstalador.lnk" "$INSTDIR\uninstall.exe"
  
  SetOutPath $INSTDIR\Aplicacion
  
  CreateShortCut "$DESKTOP\ClienteCorreo.lnk" "$INSTDIR\Aplicacion\java-runtime\bin\javaw" "--module-path $INSTDIR\Aplicacion\javafx\lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.web,javafx.base --add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED -jar $INSTDIR\Aplicacion\GmailPruebas.jar"
  CreateShortCut "$SMPROGRAMS\ClienteCorreo.lnk" "$INSTDIR\Aplicacion\java-runtime\bin\javaw" "--module-path $INSTDIR\Aplicacion\javafx\lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.web,javafx.base --add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED -jar $INSTDIR\Aplicacion\GmailPruebas.jar"
  
# Fin de la seccion
SectionEnd

# seccion del desintalador
Section "uninstall"
 
    # borramos el desintalador primero
    delete "$INSTDIR\uninstall.exe"

    RmDir /r "$INSTDIR\Aplicacion"
	
    RmDir "$INSTDIR"
	
	delete "$DESKTOP\ClienteCorreo.lnk"
	delete "$SMPROGRAMS\ClienteCorreo.lnk"
	delete "$DESKTOP\Desinstalador.lnk"
 
# fin de la seccion del desinstalador

SectionEnd

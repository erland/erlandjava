'#Reference {662901FC-6951-4854-9EB2-D9A2570F2B2E}#5.1#0#C:\WINDOWS\System32\winhttp.dll#Microsoft WinHTTP Services, version 5.1
' This script exports image data to Erland Gallery
'
'Copyright (C) 2003-2004 Mats Ekberg
'
'This program is free software; you can redistribute it and/or
'modify it under the terms of the GNU General Public License
'as published by the Free Software Foundation; either version 2
'of the License, or (at your option) any later version.
'
'This program is distributed in the hope that it will be useful,
'but WITHOUT ANY WARRANTY; without even the implied warranty of
'MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
'GNU General Public License for more details.
'
'You should have received a copy of the GNU General Public License
'along with this program; if not, write to the Free Software
'Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

Option Explicit

Const ccIncrement = 50000
Dim ccOffset As Long
Dim ccLen As Long


Sub FastConcat(Dest As String, Source As String)
	Dim L As Long
    L = Len(Source)
    If (ccOffset + L) >= ccLen Then
      If L > ccIncrement Then
        Dest = Dest & Space$(L)
        ccLen = ccLen + L
      Else
        Dest = Dest & Space$(ccIncrement)
        ccLen = ccLen + ccIncrement
      End If
    End If
    Mid$(Dest, ccOffset + 1, L) = Source
    ccOffset = ccOffset + L
End Sub

'
Sub Main

	' Get the url of the webservice
	Dim wsurl As Variant

	Dim db As Database
	Set db = Application.ActiveDatabase
	If db Is Nothing Then
		MsgBox "Please open a database first!"
		Exit Sub
	End If




	Dim selection As Images
	Set selection = db.ActiveSelection
	If selection Is Nothing Then
		If MsgBox("No images selected, do you want to export the whole database ?",vbYesNo) = vbNo Then
			Exit Sub
		Else
			Set selection = db.GetImages
		End If
	End If


	Begin Dialog UserDialog 610,294,"Erland Gallery export" ' %GRID:10,7,1,1
		Text 10,21,590,14,"This script updates the Erland Gallery with data from the selected images.",.Text1

		Text 10,70,590,14,"Name of the gallery to export to:",.Text3
		Text 10,49,590,14,"The url to the web server:",.Text6
		TextBox 250,70,150,14,.GalleryTextbox
		TextBox 250,49,340,14,.UrlTextbox
		Text 10,91,590,14,"Username for the gallery:",.Text4
		TextBox 250,91,150,14,.UsernameTextbox
		Text 10,112,590,14,"Password for the user:",.Text5
		TextBox 250,112,150,14,.PasswordTextbox,-1

		Text 10,133,590,14,"Set the export options below:",.Text2
		CheckBox 10,154,570,14,"Remove existing categories",.CheckRemoveCats
		CheckBox 10,175,570,14,"Remove existing image links",.CheckRemoveLinks
		CheckBox 10,196,570,14,"Do not publish images on the web",.CheckNoPublish
		CheckBox 10,217,570,14,"Use filename as title if title is undefined",.CheckFilenameTitle
		CheckBox 10,238,570,14,"Use filename as description if description is undefined",.CheckFilenameDescr
		OKButton 400,266,90,21
		CancelButton 500,266,90,21
	End Dialog

	Dim dlg As UserDialog
	dlg.UsernameTextbox = Application.GetUserVariable("ErlandGalleryUser")
	dlg.PasswordTextbox = Application.GetUserVariable("ErlandGalleryPass")
	dlg.GalleryTextbox = Application.GetUserVariable("ErlandGalleryName")
	dlg.UrlTextbox = Application.GetUserVariable("ErlandGalleryImportURL")
	dlg.CheckFilenameTitle = 1
	dlg.CheckFilenameDescr = 1
	If Dialog(dlg) >= 0 Then
		Exit Sub
	End If


	wsurl = dlg.UrlTextbox
	If (wsurl = "") Then
		MsgBox "No url defined"
		Exit Sub
	End If

	Dim s As String
	Dim d As Date
	Dim res As IMFileOperationResults
	Dim perc As Long
    Dim c As Category
	Dim xmlout As Variant


	perc = 0

	Application.WaitDialogOpen "Exporting images...",0,selection.Count,True

	xmlout=""
	ccOffset = 0
	ccLen = 0
	FastConcat xmlout,"<images>"
	Dim i As Image
	For Each i In selection

		If Application.WaitDialogIsCanceled Then
			Exit For
		End If

		FastConcat xmlout,"<image oid=""" & i.OID & """>"
		FastConcat xmlout,"<filename>" & i.FileName & "</filename>"
		FastConcat xmlout,"<last-modified>" & i.LastModified & "</last-modified>"
		FastConcat xmlout,"<categories>"
		For Each c In i.Categories
			FastConcat xmlout,"<category>" & c.FullName & "</category>"
		Next c
		FastConcat xmlout,"</categories>"
		FastConcat xmlout,"<properties>"
		If (i.Properties.Property("Title") <> "") Then
			FastConcat xmlout,"<property name=""Title"">" & i.Properties.Property("Title") & "</property>"
		End If
		If (i.Properties.Property("Description") <> "") Then
			FastConcat xmlout,"<property name=""Description"">" & i.Properties.Property("Description") & "</property>"
		End If
		FastConcat xmlout,"</properties>"
		FastConcat xmlout,"</image>"
		perc = perc + 1
		Application.WaitDialogSetPercentage perc
	Next i
	FastConcat xmlout,"</images>"
	wsurl = dlg.UrlTextbox
	wsurl = wsurl & "?gallery=" & dlg.GalleryTextbox & "&user=" & dlg.UsernameTextbox & "&pass=" & dlg.PasswordTextbox
	wsurl = wsurl & "&clearCategories=" & dlg.CheckRemoveCats
	wsurl = wsurl & "&clearPictures=" & dlg.CheckRemoveLinks
	wsurl = wsurl & "&localLinks=" & dlg.CheckNoPublish
	wsurl = wsurl & "&filenameAsPictureTitle=" & dlg.CheckFilenameTitle
	wsurl = wsurl & "&filenameAsPictureDescription=" & dlg.CheckFilenameDescr

    Dim req As WinHttpRequest

    ' Assemble an HTTP Request.
    Set req = New WinHttpRequest

	Debug.Print wsurl
	Debug.Print xmlout

	req.SetTimeouts 30000, 30000, 0, 0

    req.Open "POST", wsurl, False

	req.SetRequestHeader("Content-type", "text/xml; charset=UTF-8")

    'Send the HTTP Request.
    req.Send(xmlout)

    ' Put status and content type into status text box.
    Debug.Print req.Status & " - " & req.StatusText
	Debug.Print req.ResponseText

    ' Put response data into a file.
    Open "d:\response.txt" For Output As #2
    Print #2, req.ResponseText
    Close #2

	Application.WaitDialogClose

End Sub

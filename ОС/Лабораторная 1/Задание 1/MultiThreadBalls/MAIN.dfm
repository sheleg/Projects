object Form1: TForm1
  Left = 222
  Top = 165
  BorderStyle = bsDialog
  Caption = 'Thread test'
  ClientHeight = 361
  ClientWidth = 500
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnCloseQuery = FormCloseQuery
  OnCreate = FormCreate
  OnDestroy = FormDestroy
  PixelsPerInch = 96
  TextHeight = 13
  object PaintBox1: TPaintBox
    Left = 0
    Top = 0
    Width = 500
    Height = 300
    Align = alClient
  end
  object Panel1: TPanel
    Left = 0
    Top = 300
    Width = 500
    Height = 61
    Align = alBottom
    BevelInner = bvLowered
    Color = clCream
    TabOrder = 0
    object Button1: TButton
      Left = 16
      Top = 16
      Width = 75
      Height = 25
      Caption = 'Resume Red'
      TabOrder = 0
      OnClick = Button1Click
    end
    object Button2: TButton
      Left = 104
      Top = 16
      Width = 75
      Height = 25
      Caption = 'Suspend Red'
      TabOrder = 1
      OnClick = Button2Click
    end
    object Button3: TButton
      Left = 200
      Top = 16
      Width = 75
      Height = 25
      Caption = 'Resume Blue'
      TabOrder = 2
      OnClick = Button3Click
    end
    object Button4: TButton
      Left = 288
      Top = 16
      Width = 75
      Height = 25
      Caption = 'Suspend Blue'
      TabOrder = 3
      OnClick = Button4Click
    end
    object Button5: TButton
      Left = 384
      Top = 16
      Width = 97
      Height = 25
      Caption = 'Terminate all'
      TabOrder = 4
      OnClick = Button5Click
    end
  end
end

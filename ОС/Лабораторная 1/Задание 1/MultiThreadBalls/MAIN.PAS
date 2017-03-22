unit main;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls, ExtCtrls, RedThread, BlueThread, AbsBall;

const
  NUMBER_OF_BALLS = 40;

type
  TForm1 = class(TForm)
    PaintBox1: TPaintBox;
    Panel1: TPanel;
    Button1: TButton;
    Button2: TButton;
    Button3: TButton;
    Button4: TButton;
    Button5: TButton;
    procedure FormCreate(Sender: TObject);
    procedure Button1Click(Sender: TObject);
    procedure Button2Click(Sender: TObject);
    procedure Button3Click(Sender: TObject);
    procedure Button4Click(Sender: TObject);
    procedure Button5Click(Sender: TObject);
    procedure FormCloseQuery(Sender: TObject; var CanClose: Boolean);
    procedure FormDestroy(Sender: TObject);
  private
    balls: array[1..NUMBER_OF_BALLS] of TAbstractBallThread;
    ThreadsRunning: integer;
    procedure ThreadDone(Sender: TObject);
  public
  end;

var
  Form1: TForm1;

implementation

{$R *.dfm}

procedure TForm1.FormCreate(Sender: TObject);
var
  i: integer;
begin
  for i:=1 to NUMBER_OF_BALLS div 2 do begin
    balls[i] := TRedBallThread.Create(PaintBox1.Canvas);
    balls[i].OnTerminate := ThreadDone;
  end;
  for i:=NUMBER_OF_BALLS div 2 + 1 to NUMBER_OF_BALLS do begin
    balls[i] := TBlueBallThread.Create(PaintBox1.Canvas);
    balls[i].OnTerminate := ThreadDone;
  end;
  ThreadsRunning := NUMBER_OF_BALLS;
end;

procedure TForm1.Button1Click(Sender: TObject);
var
  i: integer;
begin
  for i:=1 to NUMBER_OF_BALLS do
    if balls[i] is TRedBallThread then
      (balls[i] as TRedBallThread).Resume;
end;

procedure TForm1.Button2Click(Sender: TObject);
var
  i: integer;
begin
  for i:=1 to NUMBER_OF_BALLS do
    if balls[i] is TRedBallThread then
      (balls[i] as TRedBallThread).Suspend;
end;

procedure TForm1.Button3Click(Sender: TObject);
var
  i: integer;
begin
  for i:=1 to NUMBER_OF_BALLS do
    if balls[i] is TBlueBallThread then
      (balls[i] as TBlueBallThread).Resume;
end;

procedure TForm1.Button4Click(Sender: TObject);
var
  i: integer;
begin
  for i:=1 to NUMBER_OF_BALLS do
    if balls[i] is TBlueBallThread then
      (balls[i] as TBlueBallThread).Suspend;
end;

procedure TForm1.Button5Click(Sender: TObject);
var
  i: integer;
begin
  for i:=1 to NUMBER_OF_BALLS do
    balls[i].Terminate;
end;

procedure TForm1.ThreadDone(Sender: TObject);
begin
  assert(ThreadsRunning >= 0);
  Dec(ThreadsRunning);
end;

procedure TForm1.FormCloseQuery(Sender: TObject; var CanClose: Boolean);
begin
  if ThreadsRunning = 0 then begin
    CanClose := true;
  end
  else begin
    CanClose := false;
    ShowMessage('You should terminate all threads before closing application...');
  end;
end;

procedure TForm1.FormDestroy(Sender: TObject);
var
  i: integer;
begin
  for i:=1 to NUMBER_OF_BALLS do
    balls[i].Free;
end;

end.


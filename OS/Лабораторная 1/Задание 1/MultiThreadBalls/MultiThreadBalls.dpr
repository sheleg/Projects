program MultiThreadBalls;

uses
  Forms,
  main in 'main.pas' {Form1},
  RedThread in 'RedThread.pas',
  AbsBall in 'AbsBall.pas',
  BlueThread in 'BlueThread.pas';

{$R *.res}

begin
  Application.Initialize;
  Application.CreateForm(TForm1, Form1);
  Application.Run;
end.

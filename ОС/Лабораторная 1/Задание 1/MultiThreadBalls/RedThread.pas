unit RedThread;

interface

uses AbsBall, Graphics;

type
  TRedBallThread = class(TAbstractBallThread)
  protected
    procedure ShowBall; override;     // Переопределяем рисование: рисуем красную окружность
    procedure HideBall; override;
  public
    constructor Create(aCanvas: TCanvas);
  end;

implementation

uses Classes;

{ TRedBallThread }

constructor TRedBallThread.Create(aCanvas: TCanvas);
begin
  inherited Create(aCanvas);
  // tpIdle, tpLowest, tpLower, tpNormal, tpHigher, tpHighest, tpTimeCritical
  // Разница в приоритетах будет более заметна, если потоков много и система
  // занята выполнением других действий.
  Priority := tpIdle;
end;

procedure TRedBallThread.ShowBall;
begin
  Canvas.Pen.Color := clRed;
  Canvas.Ellipse(CurrentX-2, CurrentY-2, CurrentX+2, CurrentY+2);
end;

procedure TRedBallThread.HideBall;
begin
  Canvas.Pen.Color := clBtnFace;
  Canvas.Ellipse(CurrentX-2, CurrentY-2, CurrentX+2, CurrentY+2);
end;

end.

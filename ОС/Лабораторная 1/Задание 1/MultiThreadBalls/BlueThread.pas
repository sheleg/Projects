unit BlueThread;

interface

uses AbsBall, Graphics;

type
  TBlueBallThread = class(TAbstractBallThread)
  protected
    procedure ShowBall; override;      // �������������� ���������: ������ ����� �������������
    procedure HideBall; override;
  public
    constructor Create(aCanvas: TCanvas);
  end;

implementation

uses Classes;

{ TBlueBallThread }

constructor TBlueBallThread.Create(aCanvas: TCanvas);
begin
  inherited Create(aCanvas);
  // tpIdle, tpLowest, tpLower, tpNormal, tpHigher, tpHighest, tpTimeCritical
  // ������� � ����������� ����� ����� �������, ���� ������� ����� � �������
  // ������ ����������� ������ ��������.
  Priority := tpHighest;
end;

procedure TBlueBallThread.ShowBall;
begin
  Canvas.Pen.Color := clBlue;
  Canvas.Rectangle(CurrentX-2, CurrentY-2, CurrentX+2, CurrentY+2);
end;

procedure TBlueBallThread.HideBall;
begin
  Canvas.Pen.Color := clBtnFace;
  Canvas.Rectangle(CurrentX-2, CurrentY-2, CurrentX+2, CurrentY+2);
end;

end.

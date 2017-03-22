unit AbsBall;

interface

uses
  Classes, Graphics;

type
  // ����������� �����, ������������ � �������� �������� ��� ��������
  // �������� ������. � ���� ������ ������� ��� ����������������,
  // ��������� � ���������� ��������� � ������������ ������ � ���������
  // ������. ������� ������ ���� �������������� ������ ShowBall � HideBall,
  // ������� �������� ���, ��������� � ���������� / ��������� ������������
  // ������������� ������.
  TAbstractBallThread = class(TThread)
  private
    fCanvas: TCanvas;
    x, y, dx, dy: integer;
  protected
    procedure ShowBall; virtual; abstract;
    procedure HideBall; virtual; abstract;
    property Canvas: TCanvas read fCanvas;
    property CurrentX: integer read x;
    property CurrentY: integer read y;
    procedure DoStep;
    procedure Execute; override;
  public
    constructor Create(aCanvas: TCanvas);
  end;

implementation

uses SysUtils;

{ TRedBallThread }

constructor TAbstractBallThread.Create(aCanvas: TCanvas);
begin
  inherited Create(true);
  FreeOnTerminate := false;   // ����������� ���������� � TForm1.FormDestroy
  fCanvas := aCanvas;
  Randomize;
  x := Random(500);
  y := Random(300);
  dx := Random(2) * 2 - 1;
  dy := Random(2) * 2 - 1;
end;

procedure TAbstractBallThread.DoStep;
begin
  Synchronize(HideBall);
  x := x + dx;
  y := y + dy;
  // ��� ������� ���������� ������� ����� ����� ��� ���� ����� Synchronize()
  Synchronize(ShowBall);
  // ShowBall;
  if (x < 2) or (x > 498) then dx := -dx;
  if (y < 2) or (y > 298) then dy := -dy;
end;

// "�����������" ������ ����������� ������ ������: ����� ��������� ���� �����������
// ������ (DoStep), ����� "��������" (����� ��� ����� ����������� ��������� �����������
// ������ ������ �������). ���� ������� �����������, ���� ���� Terminated ����� ��������
// false. ���� Terminated = true, �� ����� �����������(!!!) �����������.
procedure TAbstractBallThread.Execute;
begin
  while not Terminated do begin
    DoStep;
    Sleep(25);
  end;
end;

end.


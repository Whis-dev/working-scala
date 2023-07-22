import zio._

abstract class Notification
case class Email(sourceEmail: String, title: String, body: String) extends Notification
case class SMS(sourceNumber: String, message: String) extends Notification
case class VoiceRecording(contactName: String, link: String) extends Notification

case class Board(title: String, txt: String, notification: List[Notification])

object Main extends ZIOAppDefault {
  // override def run = for {
  //   _ <- Console.printLine("run!")
  // } yield ()

  override val run = for {
    _ <- ZIO.unit
    b = Board("a", "title", 
      List(
        Email("whisdevj@gmail.com", "example", "It is test"),
        SMS("1", "SMS test!"),
        SMS("2", "SMS test!"),
        VoiceRecording("whis", "https://link.example.com")
        )
      )
    _ = println(b)

    // patter matching
    _ = b.notification.foreach{noti => 
      val a = noti match {
        case Email(sourceEmail, title, body) => s"이 이메일은 영국에서 시작되었습니다 제목은 $title 인데..."
        case SMS(sourceNumber, message) if sourceNumber == "1" => "차단" 
        case SMS(sourceNumber, message) => s"국제번호에서 온 SMS 입니다 $sourceNumber, $message"
        case VoiceRecording(contactName, link) => s"텍스트는 들려줄수 없습니다 $contactName 직접 전화걸어주세요"
        case _ => "뭐죠...?"
      }
    
      println(a)
    }
  } yield()
}

// implicit, macro 특이한 스칼라문법은 안할거에요 + monad도 안할거에요~
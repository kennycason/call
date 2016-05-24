class Call < Formula
  desc "Call: Aliases made easy"
  homepage "https://github.com/kennycason/call"
  url "http://search.maven.org/remotecontent?filepath=com/kennycason/call/1.0/call-1.0.jar"
  sha256 "bad03409262be9b6ded7bd1a900209c13feecc415dcedad5b735ce76039f5f32"

  def install
    libexec.install "call-1.0.jar"
    bin.write_jar_script libexec/"call-1.0.jar", "call"
    puts "Finished installing Call 1.0"
  end

  def server_script(server_jar); <<-EOS.undent
    #!/bin/bash
    exec java -cp #{server_jar} com.kennycason.call.CallKt "$@"
  EOS
  end

  test do
    pipe_output("#{bin}/call version", "Test Call version command")
  end
end

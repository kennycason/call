class Call < Formula
  desc "Call: Aliases made easy"
  homepage "https://github.com/kennycason/call"
  url "http://search.maven.org/remotecontent?filepath=com/kennycason/call/1.0/call-1.0.jar"
  sha256 "cb48bed0fb115f3849284467990e121a938897f67faef5c9d462af74af05887c"

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

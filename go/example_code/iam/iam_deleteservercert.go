package main

import (
	"fmt"

	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/aws/session"
	"github.com/aws/aws-sdk-go/service/iam"
)

// Usage:
// go run iam_deleteservercert.go
func main() {
	// Initialize a session that the SDK will use to load configuration,
	// credentials, and region from the shared config file. (~/.aws/config).
	sess := session.Must(session.NewSessionWithOptions(session.Options{
		SharedConfigState: session.SharedConfigEnable,
	}))

	// Create a IAM service client.
	svc := iam.New(sess)

	_, err := svc.DeleteServerCertificate(&iam.DeleteServerCertificateInput{
		ServerCertificateName: aws.String("CERTIFICATE_NAME"),
	})
	if err != nil {
		fmt.Println("Error", err)
		return
	}

	fmt.Println("Server certificate deleted")
}
